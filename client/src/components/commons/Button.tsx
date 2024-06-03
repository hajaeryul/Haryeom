import React, { MouseEventHandler, ReactNode } from 'react';
import styled from 'styled-components';

interface StyleProps {
    width?: string;
    height?: string;
    padding?: string;
    color?: string;
    $backgroundColor?: string;
    $borderColor?: string;
    $borderRadius?: string;
}

interface ButtonProps extends StyleProps {
    content: string | ReactNode;
    onClick: MouseEventHandler<HTMLButtonElement>;
}

const Button = (props: ButtonProps) => {
    const { content, onClick, ...styleProps } = props;
    return (
        <StyledButton onClick={onClick} {...styleProps}>
            {content}
        </StyledButton>
    );
};

const StyledButton = styled.button<StyleProps>`
    cursor: pointer;
    background-color: transparent;
    border: none;
    width: ${({ width }) => (width ? width : '10px')};
    height: ${({ height }) => (height ? height : '10px')};
    padding: ${({ padding }) => (padding ? padding : '0')};
    ${({ $backgroundColor }) => $backgroundColor && `background-color: ${$backgroundColor}`};
    ${({ color }) => color && `color: ${color}`};
    ${({ $borderColor }) => $borderColor && `border: 1px solid ${$borderColor}`};
    ${({ $borderRadius }) => $borderRadius && `border-radius: ${$borderRadius}`};
    display: flex;
    align-items: center;
    justify-content: center;

    svg {
        height: 100%;
    }
`;

export default Button;
